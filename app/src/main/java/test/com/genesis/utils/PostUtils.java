package test.com.genesis.utils;

import java.util.ArrayList;
import java.util.List;

import test.com.genesis.pojo.Attachment;
import test.com.genesis.pojo.Attachments;

/**
 * Created by dima on 08.12.16.
 */

public class PostUtils {
    public static List<String> getImages(Attachments attachments) {
        List<String> list = new ArrayList<>();
        if (attachments == null || attachments.data == null) return list;
        for (Attachment attachment : attachments.data) {
            if (attachment.type.equals(Attachment.TYPE_PHOTO)
                    || attachment.type.equals(Attachment.TYPE_IMAGE_SHARE)) {
                list.add(attachment.media.image.src);
            } else if (attachment.type.equals(Attachment.TYPE_ALBUM)) {
                list.addAll(getImages(attachment.subattachments));
            }
        }
        return list;
    }
}
