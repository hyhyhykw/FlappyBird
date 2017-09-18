//package hy.flappybird.utils;
//
//import android.content.Context;
//import android.util.Log;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//
//import hy.flappybird.R;
//import hy.flappybird.sprite.Frame;
//import hy.flappybird.sprite.FrameBean;
//import hy.flappybird.sprite.SourceSize;
//import hy.flappybird.sprite.Stripe;
//
///**
// * Created time : 2017/8/23 11:44.
// *
// * @author HY
// */
//
//public class RRead {
//
//    public static Stripe getSpriteInfo(Context context) {
////        List<SpriteInfo> list = new ArrayList<>();
//        ArrayList<Frame> frames = new ArrayList<>();
//        InputStream inputStream = context.getResources().openRawResource(R.raw.atlas);
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//
//        try {
//            String s = br.readLine();
//
//            while (s != null && !s.equals("")) {
//                String[] split = s.split("\\s+");
//
//
////                SpriteInfo info = new SpriteInfo(split[0],
////                        Integer.parseInt(split[1]),
////                        Integer.parseInt(split[2]),
////                        1024 * Float.parseFloat(split[3]),
////                        1024 * Float.parseFloat(split[4]),
////                        1024 * Float.parseFloat(split[5]),
////                        1024 * Float.parseFloat(split[6]));
//                /////////////////////////////////////////////////////////////////
//                FrameBean frameBean = new FrameBean((int) (1024 * Float.parseFloat(split[3])),
//                        (int) (1024 * Float.parseFloat(split[4])),
//                        Integer.parseInt(split[1]),
//                        Integer.parseInt(split[2]));
//
//                FrameBean spriteSourceSize = new FrameBean(0, 0, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
//
//                SourceSize sourceSize = new SourceSize(Integer.parseInt(split[1]),
//                        Integer.parseInt(split[2]));
//
//                Frame frame = new Frame(split[0], frameBean, spriteSourceSize, sourceSize, false, false);
//                frames.add(frame);
//
//                ////////////////////////////////////////////////////
//
////                Log.e("TAG", info.toString());
////                list.add(info);
//
//                s = br.readLine();
//            }
//
//        } catch (IOException e) {
//            Log.e("Error   ", "", e);
//        }
//        return new Stripe(frames);
//    }
//}
