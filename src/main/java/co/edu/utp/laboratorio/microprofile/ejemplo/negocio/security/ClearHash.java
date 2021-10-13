package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security;

import javax.security.enterprise.identitystore.PasswordHash;

public class ClearHash implements PasswordHash {
    @Override
    public String generate(char[] password) {
        return new String(password);
    }

    @Override
    public boolean verify(char[] password, String hashedPassword) {
        return new String(password).equals(hashedPassword);
    }
}
