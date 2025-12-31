// SPDX-License-Identifier: MIT
pragma solidity ^0.8.24;

import "forge-std/Test.sol";
import "../src/SignatureVerifier.sol";

contract SignatureVerifierTest is Test {

    SignatureVerifier verifier;

    function setUp() public {
        verifier = new SignatureVerifier();
    }

    function testMessageHashNotZero() public {
        bytes32 hash = verifier.getMessageHash(
            "test-token",
            "test-uuid"
        );
        assertTrue(hash != bytes32(0));
    }
}