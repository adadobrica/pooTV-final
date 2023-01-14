package database;

public final class DatabaseFactory {
    /**
     * Created a Factory to generate objects based on its specific action
     * @param action a string containing the action
     * @return the new object
     */
    public DatabaseAction databaseAction(final String action) {
        if (action.equals("delete")) {
            return new DatabaseDelete();
        }
        if (action.equals("add")) {
            return new DatabaseAdd();
        }
        return null;
    }
}
