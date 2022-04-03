class Forlag {
    private String navn;
    private String sted;

    public Forlag(String navn, String sted) {
        this.navn = navn;
        this.sted = sted;
    }

    @Override
    public String toString() {
        return navn + ", " + sted;
    }
}

class Tidsskrift {
    private String titel;
    private Forlag forlag;
    private String issn;

    public Tidsskrift(String titel) {
        this.titel = titel;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public void setForlag(Forlag forlag) {
        this.forlag = forlag;
    }

    @Override
    public String toString() {
        return titel;
    }
}

class Artikel {
    private String[] forfattere;
    private String titel;
    private Tidsskrift tidsskrift;
    private Artikel[] referenceliste;

    public Artikel(String[] forfattere, String titel, Tidsskrift tidsskrift) {
        this.forfattere = forfattere;
        this.titel = titel;
        this.tidsskrift = tidsskrift;
    }

    public void setReferenceliste(Artikel[] referenceliste) {
        this.referenceliste = referenceliste;
    }

    @Override
    public String toString() {
        String formattedAuthors;
        if (forfattere.length == 0) {
            formattedAuthors = "Unknown";
        } else if (forfattere.length == 1) {
            formattedAuthors = forfattere[0];
        } else {
            // First author is written normally
            formattedAuthors = forfattere[0];

            // Every subsequent author (excluding the last one) is comma-seperated
            for (int i = 1; i < forfattere.length - 1; i++) {
                formattedAuthors += ", " + forfattere[i];
            }
            
            // And the last author is seperated by "and"
            formattedAuthors += " & " + forfattere[forfattere.length-1];
        }

        return formattedAuthors + ": “" + titel + "”. " + tidsskrift.toString();
    }
}

public class ArtikelTest {
    public static void main(String[] args) {
        Forlag uniForlag = new Forlag("University Press", "Denmark");

        Tidsskrift logicTidsskrift = new Tidsskrift("Journal of Logic");
        logicTidsskrift.setForlag(uniForlag);
        Tidsskrift brainTidsskrift = new Tidsskrift("Brain");
        brainTidsskrift.setForlag(uniForlag);

        Artikel artikelA = new Artikel(new String[]{"A. Abe", "A. Turing"}, "A", logicTidsskrift);
        Artikel artikelB = new Artikel(new String[]{"B. Bim"}, "B", logicTidsskrift);
        // Extra article!
        Artikel artikelC = new Artikel(new String[]{"C. Boutet-Livoff", "P. Olly", "C. lémentine"}, "C for yourself", brainTidsskrift);

        artikelA.setReferenceliste(new Artikel[]{artikelB});

        System.out.println(artikelA);
        System.out.println(artikelB);
        System.out.println(artikelC);
    }
}