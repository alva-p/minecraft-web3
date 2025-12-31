const express = require("express");
const fs = require("fs");
const cors = require("cors");
const { ethers } = require("ethers");

const app = express();
app.use(cors());
app.use(express.json());

const DB_FILE = "./db.json";

/**
 * Construye EXACTAMENTE el mismo mensaje que:
 * - Foundry (SignatureVerifier.sol)
 * - Frontend (wagmi signMessage)
 */
function buildMessage(token, uuid) {
  return `Link Minecraft wallet
Token:${token}
UUID:${uuid}`;
}

/* ======================
   DB helpers
====================== */

function readDB() {
  if (!fs.existsSync(DB_FILE)) {
    return { links: {} };
  }
  return JSON.parse(fs.readFileSync(DB_FILE, "utf8"));
}

function writeDB(data) {
  fs.writeFileSync(DB_FILE, JSON.stringify(data, null, 2));
}

/* ======================
   Routes
====================== */

// Health check
app.get("/health", (req, res) => {
  res.json({ status: "ok" });
});

/**
 * Confirmar vinculación (SEGURA)
 * Requiere firma válida
 */
app.post("/link/confirm", async (req, res) => {
  const { token, uuid, wallet, signature } = req.body;

  if (!token || !uuid || !wallet || !signature) {
    return res.status(400).json({
      error: "token, uuid, wallet y signature son requeridos",
    });
  }

  try {
    // 1. Reconstruir mensaje
    const message = buildMessage(token, uuid);

    // 2. Recuperar signer desde la firma
    const recoveredAddress = ethers.verifyMessage(message, signature);

    // 3. Comparar con wallet enviada
    if (recoveredAddress.toLowerCase() !== wallet.toLowerCase()) {
      return res.status(401).json({ error: "Firma inválida" });
    }

    // 4. Guardar vinculación
    const db = readDB();

    db.links[token] = {
      wallet,
      uuid,
      linkedAt: new Date().toISOString(),
    };

    writeDB(db);

    return res.json({ success: true });
  } catch (err) {
    console.error("Error verificando firma:", err);
    return res.status(500).json({ error: "Error verificando firma" });
  }
});

/**
 * Consultar estado del token
 */
app.get("/link/status", (req, res) => {
  const { token } = req.query;

  if (!token) {
    return res.status(400).json({ error: "token requerido" });
  }

  const db = readDB();
  const entry = db.links[token];

  if (!entry) {
    return res.json({ linked: false });
  }

  return res.json({
    linked: true,
    wallet: entry.wallet,
    uuid: entry.uuid,
  });
});

/* ======================
   Server
====================== */

app.listen(3000, () => {
  console.log("Backend Web3 escuchando en http://localhost:3000");
});
