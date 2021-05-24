package m2l.gestion.gestsallemob;

public class Salle
{
    public Salle() {
    }

    public Salle(int id) {
        this.id = id;
    }

    public Salle(int id, String nom, int capacite, String equipement, String services) {
        this.id = id;
        this.nom = nom;
        this.capacite = capacite;
        this.equipement = equipement;
        this.services = services;
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setEquipement(String equipement) {
        this.equipement = equipement;
    }

    public void setServices(String services) {
        this.services = services;
    }

    private String nom;
    private int capacite;

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getEquipement() {
        return equipement;
    }

    public String getServices() {
        return services;
    }

    private String equipement;
    private String services;
}
