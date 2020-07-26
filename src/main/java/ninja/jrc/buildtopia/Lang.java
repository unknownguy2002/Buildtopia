package ninja.jrc.buildtopia;

public enum Lang {
    // Errors
    INSUFFICIENT_ARGUMENTS("&cInsufficient argumens!"),
    PLAYER_ONLY("&cOnly players can perform this action!"),
    NAME_INVALID_CHARACTERS("&cWorld names can only contain A-Z, a-z, 0-9 and underscores!"),
    NAME_TOO_LONG("&cWorld name is too long!"),
    WORLD_EXISTS("&cWorld already exists!"),
    WORLD_DOES_NOT_EXIST("&cWorld does not exist!"),
    CANNOT_FIND_PLAYER("&cCannot find player!"),
    NO_EDIT_ACCESS("&cYou do not have edit access!"),

    // Success
    WORLD_CREATED("&aWorld created!"),
    PERMISSION_GRANTED("&aPermission granted to %s"),
    PERMISSION_UNGRANTED("&aPermission ungranted to %s")
    ;



    private String phrase;

    Lang(String phrase){
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }

    public String getPhraseWithPrefix(){
        return "&8[&aBuildtopia&8]&r "+phrase;
    }
}
