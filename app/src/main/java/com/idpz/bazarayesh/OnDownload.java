package com.idpz.bazarayesh;

public interface OnDownload {
    void downloadPr(String messageText);

    void OnDownloadComplete(String fileName);
}
