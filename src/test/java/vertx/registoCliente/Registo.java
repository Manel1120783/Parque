package vertx.registoCliente;

/**
 *
 * @author Misterio
 */
public class Registo {

    int Id;

    // private String apelido;
    public Registo() {
        nome = "X";
        apelido = "B";
        email = "Y";
        NIF = 000000000;
        contacto = 000000000;
        QR = "A";
    }

    public Registo(String nome, String apelido, String email, long NIF, long contacto, String QR) {
        this.nome = nome;
        this.email = email;
        this.apelido = apelido;
        this.NIF = NIF;
        this.contacto = contacto;
        this.QR = QR;
    }

    Registo(String nome, String apelido, String email, long parseLong, String contacto, long parseLong0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    Registo(String manuel, String nome, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setNIF(long NIF) {
        this.NIF = NIF;
    }

    public void setContacto(long contacto) {
        this.contacto = contacto;
    }

    public void setQR(String QR) {
        this.QR = QR;
    }

    String nome, email, apelido, QR;
    long NIF, contacto;

// necessário ser public para enviar como JSON
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getApelido() {
        return apelido;
    }
    
    public long getNIF() {
        return NIF;
    }
    
    public long getContacto() {
        return contacto;
    }
    
    public String getQR() {
        return QR;
    }

    @Override
    public String toString() {
        return "{" + "\"Nome\":\"" + nome + "\"Apelido\":\"" + apelido + "\", \"email\":\"" + email + "\", \"NIF\":\"" + NIF + "\"Contacto\":\"" + contacto + "\"QR\":\"" + QR + "\"}";
    }

}
