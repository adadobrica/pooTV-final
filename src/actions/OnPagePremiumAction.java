package actions;

import user.User;
import user.UserController;

public final class OnPagePremiumAction extends PageAction {
    private String feature;

    public OnPagePremiumAction(final String type,
                               final String page,
                               final String feature) {
        super(type, page);
        this.feature = feature;
    }

    public OnPagePremiumAction() {
        super(null, null);
    }

    /**
     * Method that upgrades the user to premium
     * @param currentUser information about the current user
     * @return the current user
     */
    public User upgradePremium(final User currentUser) {
        new UserController().setPremium(currentUser);
        return currentUser;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }


    @Override
    public String toString() {
        return "OnPagePremiumAction{"
                + "feature='" + feature + '\''
                + '}';
    }
}
