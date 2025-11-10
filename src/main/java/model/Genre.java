package model;

public class Genre {
    private Integer id;
    private String libelle;

    public Genre() { }                            // constructeur vide (obligatoire pour JSP/JDBC)

    public Genre(int id, String libelle) {        // ✅ constructeur utilisé dans ton DAO
        this.id = id;
        this.libelle = libelle;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    @Override public String toString() { return libelle; }
}
