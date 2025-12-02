public class Main {
    public static void main(String[] args) {
        User user = SignIn.signIn();
        System.out.println();
        if (user instanceof Client) {
            System.out.println(FileUtils.retrieveUserSessions((Client) user));
            System.out.println();
        }
    }
}