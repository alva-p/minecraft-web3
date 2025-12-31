/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.minecraftweb3.service;

public class BackendStatus {

    public enum State {
        LINKED,
        NOT_LINKED,
        ERROR
    }

    public final State state;
    public final String wallet;
    public final String error;

    private BackendStatus(State state, String wallet, String error) {
        this.state = state;
        this.wallet = wallet;
        this.error = error;
    }

    public static BackendStatus linked(String wallet) {
        return new BackendStatus(State.LINKED, wallet, null);
    }

    public static BackendStatus notLinked() {
        return new BackendStatus(State.NOT_LINKED, null, null);
    }

    public static BackendStatus error(String error) {
        return new BackendStatus(State.ERROR, null, error);
    }
}

