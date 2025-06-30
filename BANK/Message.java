package BANK;

public interface Message {
    /**
     * message that sent must be handling by a class in top of them like post office
     * @return a boolean as accept-> true or refuse -> false
     */
    void sendMessage(Letter form);
    void receiveMessage(Letter form);
    boolean checkMessage();
    void deleteMessage(Letter form);
}
