// IASMessageService.aidl
package com.hatenablog.zyxwv.androidas;
import com.hatenablog.zyxwv.androidas.IMessageCallback;
import com.hatenablog.zyxwv.androidas.ASMessage;

interface IASMessageService {
    oneway void registerCallback(IMessageCallback callback);
    oneway void unregisterCallback(IMessageCallback callback);

    ASMessage[] getLatestMessages();
}
