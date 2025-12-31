// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

/// @title SignatureVerifier
/// @notice Fuente de verdad criptográfica para vincular wallets con Minecraft
contract SignatureVerifier {

    /// @notice Devuelve el hash del mensaje lógico a firmar
    function getMessageHash(
        string memory token,
        string memory uuid
    ) public pure returns (bytes32) {
        return keccak256(
            abi.encodePacked(
                "Link Minecraft wallet\n",
                "Token:",
                token,
                "\nUUID:",
                uuid
            )
        );
    }

    /// @notice Devuelve el hash EIP-191 que firma Ethereum
    function getEthSignedMessageHash(
        bytes32 messageHash
    ) public pure returns (bytes32) {
        return keccak256(
            abi.encodePacked(
                "\x19Ethereum Signed Message:\n32",
                messageHash
            )
        );
    }

    /// @notice Recupera el address firmante
    function recoverSigner(
        bytes32 ethSignedMessageHash,
        bytes memory signature
    ) public pure returns (address) {
        (bytes32 r, bytes32 s, uint8 v) = splitSignature(signature);
        return ecrecover(ethSignedMessageHash, v, r, s);
    }

    function splitSignature(
        bytes memory sig
    ) internal pure returns (bytes32 r, bytes32 s, uint8 v) {
        require(sig.length == 65, "Invalid signature length");

        assembly {
            r := mload(add(sig, 32))
            s := mload(add(sig, 64))
            v := byte(0, mload(add(sig, 96)))
        }
    }
}
