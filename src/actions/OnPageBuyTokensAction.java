package actions;

import user.User;
import user.UserController;

public final class OnPageBuyTokensAction extends PageAction {
    private String feature;
    private String count;

    public OnPageBuyTokensAction(final String type, final String page,
                                 final String feature,
                                 final String count) {
        super(type, page);
        this.feature = feature;
        this.count = count;
    }

    public OnPageBuyTokensAction() {
        super(null, null);
    }

    /**
     * Method that allows the user to buy new tokens
     * @param countNum the number of the tokens bought
     * @param currentUser information about the current user
     * @return the current user
     */

    public User buyTokens(final int countNum, final User currentUser) {
        int userBalance = Integer.parseInt(currentUser.getCredentials().getBalance());
        userBalance -= countNum;
        String newBalance = String.valueOf(userBalance);

        new UserController().setNewTokensBought(currentUser, newBalance, countNum);

        return currentUser;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(final String feature) {
        this.feature = feature;
    }

    public String getCount() {
        return count;
    }

    public void setCount(final String count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "OnPageBuyTokensAction{"
                + "feature='" + feature + '\''
                + ", count='" + count + '\''
                + '}';
    }
}
