package net.danteh.dantehviewer;

class Login {
    private int id;
    private String username;

    public Login(String username, int id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }
}
