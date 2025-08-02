package com.alurachallenges.foro_hub.infra.exceptions;

public class IntegrityValidation extends RuntimeException {
    public IntegrityValidation(String message) {
        super(message);
    }
}
