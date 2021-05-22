package com.qianmo.minepanel.Server.FTP;

import org.apache.ftpserver.ftplet.*;

import java.io.IOException;

public class FTPLet extends DefaultFtplet {
    @Override
    public FtpletResult onUploadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo services...
        return super.onUploadStart(session, request);
    }


    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo services...
        return super.onUploadEnd(session, request);
    }

    @Override
    public FtpletResult onDownloadStart(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo services...
        return super.onDownloadStart(session, request);
    }

    @Override
    public FtpletResult onDownloadEnd(FtpSession session, FtpRequest request) throws FtpException, IOException {
        //todo services...
        return super.onDownloadEnd(session, request);

    }
}
