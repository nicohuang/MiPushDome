package hwz.com.mipushdome;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

/**
 * 定义广播接收器
 */

public class DemoMessageReceiver extends PushMessageReceiver
{
    private static String log = "";

    /**
     * 接收服务器向客户端发送的透传消息
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message)
    {
        String log = context.getString(R.string.recv_passthrough_message);
        Message msg = Message.obtain();
        msg.obj = log;
        DemoApplication.getHandler().sendMessage(msg);
    }

    /**
     * 接收服务器向客户端发送的通知消息，这个回调方法会在用户手动点击通知后触发
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message)
    {
        String log = context.getString(R.string.click_notification_message);
        Message msg = Message.obtain();
        if (message.isNotified())
        {
            msg.obj = log;
        }
        DemoApplication.getHandler().sendMessage(msg);
    }

    /**
     * 接收服务器向客户端发送的通知消息，
     * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数
     *
     * @param context
     * @param message
     */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message)
    {
        String log = context.getString(R.string.arrive_notification_message);
        Message msg = Message.obtain();
        msg.obj = log;
        DemoApplication.getHandler().sendMessage(msg);
    }

    /**
     * 接收客户端向服务器发送命令后的响应结果
     *
     * @param context
     * @param message
     */
    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message)
    {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        //判断注册是否成功
        if (MiPushClient.COMMAND_REGISTER.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.register_success);
            } else
            {
                log = context.getString(R.string.register_fail);
            }
        }
        //判断设置别名是否成功
        else if (MiPushClient.COMMAND_SET_ALIAS.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.set_alias_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.set_alias_fail, message.getReason());
            }
        }
        //撤销别名
        else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.unset_alias_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.unset_alias_fail, message.getReason());
            }
        }
        //设置账号
        else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.set_account_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.set_account_fail, message.getReason());
            }
        }
        //撤销账号
        else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.unset_account_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.unset_account_fail, message.getReason());
            }
        }
        //订阅标签
        else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.subscribe_topic_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.subscribe_topic_fail, message.getReason());
            }
        }
        //撤销账号
        else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.unsubscribe_topic_success, cmdArg1);
            } else
            {
                log = context.getString(R.string.unsubscribe_topic_fail, message.getReason());
            }
        }
        //设置时间
        else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.set_accept_time_success, cmdArg1, cmdArg2);
            } else
            {
                log = context.getString(R.string.set_accept_time_fail, message.getReason());
            }
        }
        //停止接收
        else
        {
            log = message.getReason();
        }
        Message msg = Message.obtain();
        msg.obj = log;
        DemoApplication.getHandler().sendMessage(msg);
    }

    /**
     * 接收客户端向服务器发送注册命令后的响应结果
     *
     * @param context
     * @param message
     */
    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message)
    {
        String command = message.getCommand();
        String log;
        if (MiPushClient.COMMAND_REGISTER.equals(command))
        {
            if (message.getResultCode() == ErrorCode.SUCCESS)
            {
                log = context.getString(R.string.register_success);
            } else
            {
                log = context.getString(R.string.register_fail);
            }
        } else
        {
            log = message.getReason();
        }

        Message msg = Message.obtain();
        msg.obj = log;
        DemoApplication.getHandler().sendMessage(msg);
    }

//    @SuppressLint("SimpleDateFormat")
//    public static String getSimpleDate()
//    {
//        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
//    }

    /**
     * 接收message进行操作
     */
    public static class DemoHandler extends Handler
    {

        private Context context;

        public DemoHandler(Context context)
        {
            this.context = context;
        }

        @Override
        public void handleMessage(Message msg)
        {
            String message = (String) msg.obj;
            if (!TextUtils.isEmpty(message))
            {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
