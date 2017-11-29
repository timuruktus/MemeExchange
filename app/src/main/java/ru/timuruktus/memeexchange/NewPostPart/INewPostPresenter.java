package ru.timuruktus.memeexchange.NewPostPart;

import android.content.Intent;

public interface INewPostPresenter{

    void onActivityResult(int requestCode, int resultCode, Intent data);
    void onSendMemeButtonClicked();
    void onTextSettingsClicked(int gravity, int textSize, int textShadow);
    void onViewCreated();
}
