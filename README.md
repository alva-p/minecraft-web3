# Minecraft Web3 Wallet Linking

Sistema que permite vincular una wallet Web3 (Ethereum Sepolia)
con un jugador de Minecraft usando firmas criptográficas reales.

##  Componentes

- **Plugin Minecraft (Paper)**  
  Genera tokens, expone comandos `/wallet link` y `/wallet status`.

- **Backend (Node.js + Express)**  
  Verifica firmas ECDSA y gestiona el estado de vinculación.
# minecraft-web3

- **Frontend (Next.js + RainbowKit)**  
  Conecta wallets y solicita firma del mensaje.

- **Web3 (Foundry)**  
  Contrato de referencia para validación de firmas.

##  Seguridad

- Firma criptográfica real
- Backend no confía en frontend
- Mensaje reproducible y verificable
- Anti-spoofing

##  Stack

- Java 17 / PaperMC
- Node.js / Express
- Next.js / RainbowKit / wagmi
- Foundry / Solidity
