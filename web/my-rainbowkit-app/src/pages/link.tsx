import { useRouter } from 'next/router';
import { ConnectButton } from '@rainbow-me/rainbowkit';
import { useAccount, useChainId, useSignMessage } from 'wagmi';
import { sepolia } from 'wagmi/chains';
import { useEffect, useState } from 'react';

export default function LinkPage() {
  const router = useRouter();
  const token = router.query.token as string | undefined;
  const uuid = router.query.uuid as string | undefined;

  const { address, isConnected } = useAccount();
  const chainId = useChainId();
  const { signMessageAsync } = useSignMessage();

  const [status, setStatus] =
    useState<'idle' | 'signing' | 'sending' | 'done' | 'error'>('idle');

  async function handleSignAndSend() {
    if (!token || !uuid || !address) return;

    try {
      setStatus('signing');

      const message =
`Link Minecraft wallet
Token:${token}
UUID:${uuid}`;

      const signature = await signMessageAsync({ message });

      setStatus('sending');

      const res = await fetch('http://localhost:3000/link/confirm', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          token,
          uuid,
          wallet: address,
          signature,
        }),
      });

      if (!res.ok) throw new Error();

      setStatus('done');
    } catch (e) {
      setStatus('error');
    }
  }

  return (
    <main style={{ padding: 40 }}>
      <h1>Vincular Wallet con Minecraft</h1>

      {!token || !uuid ? (
        <p>❌ Token o UUID inválido</p>
      ) : (
        <>
          <ConnectButton />

          {isConnected && chainId !== sepolia.id && (
            <p>⚠️ Cambiá a la red Sepolia</p>
          )}

          {isConnected && chainId === sepolia.id && status === 'idle' && (
            <button onClick={handleSignAndSend}>
              Firmar y vincular wallet
            </button>
          )}

          {status === 'signing' && <p>✍️ Firmando mensaje...</p>}
          {status === 'sending' && <p>📡 Enviando verificación...</p>}
          {status === 'done' && <p>✅ Wallet vinculada correctamente</p>}
          {status === 'error' && <p>❌ Error al vincular wallet</p>}
        </>
      )}
    </main>
  );
}
