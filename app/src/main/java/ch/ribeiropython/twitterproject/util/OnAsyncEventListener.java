package ch.ribeiropython.twitterproject.util;

public interface OnAsyncEventListener {
    void onSuccess();
    void onFailure(Exception e);
}
