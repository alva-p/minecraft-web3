# Minecraft Web3 Wallet Linking

A system that allows you to link a Web3 wallet (Ethereum Sepolia)
with a Minecraft player using real cryptographic signatures.

## Components

- **Minecraft Plugin (Paper)**
Generates tokens, exposes the `/wallet link` and `/wallet status` commands.

- **Backend (Node.js + Express)**
Verifies ECDSA signatures and manages the linking status.

# minecraft-web3

- **Frontend (Next.js + RainbowKit)**
Connects wallets and requests message signatures.

- **Web3 (Foundry)**
Reference contract for signature validation.

## Security

- Real cryptographic signature
- Backend does not trust frontend
- Reproducible and verifiable message
- Anti-spoofing

## Stack

- Java 17 / PaperMC
- Node.js / Express
- Next.js / RainbowKit / wagmi
- Foundry / Solidity

## Image
<img width="1497" height="786" alt="mineweb3" src="https://github.com/user-attachments/assets/14bd8b02-d331-4a33-a486-f8fde915e9de" />

