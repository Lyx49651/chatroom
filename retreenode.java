package chat;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class retreenode extends DefaultMutableTreeNode {
    protected ImageIcon icon;
    protected long userAccount;
    protected String NickName;
    protected long isOnline;

    /**
     * 只包含好友昵称和是否在线的节点构造函数	 *
     *
     * @param NickName * @param isOnline
     */
    public retreenode(String NickName, long isOnline) {
        super();
        this.NickName = NickName;
        this.isOnline = isOnline;
    }

    /**
     * 包含好友头像、昵称、是否在线的节点构造函数
     * * @param icon
     * * @param NickName
     * * @param isOnline
     */
    public retreenode(ImageIcon icon, String NickName, long isOnline) {
        super();
        this.icon = icon;
        this.NickName = NickName;
        this.isOnline = isOnline;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public long getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(long isOnline) {
        this.isOnline = isOnline;
    }

    public long getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(long userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Returns the result of sending <code>toString()</code> to this node's
     * user object, or the empty string if the node has no user object.
     *
     * @see #getUserObject
     */
    public String toString() {
        if (userObject == null) {
            return "";
        } else {
            return userObject.toString();
        }
    }
}
