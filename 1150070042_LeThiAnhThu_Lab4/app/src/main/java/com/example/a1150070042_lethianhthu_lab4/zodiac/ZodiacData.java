package com.example.a1150070042_lethianhthu_lab4.zodiac;

import com.example.a1150070042_lethianhthu_lab4.R;
import java.util.ArrayList;

public class ZodiacData {
    public static ArrayList<Zodiac> getZodiacs() {
        ArrayList<Zodiac> list = new ArrayList<>();

        list.add(new Zodiac("Bạch Dương", "21/03 - 19/04", R.drawable.bachduong,
                "Người tiên phong đầy nhiệt huyết, dũng cảm và thẳng thắn. Họ luôn sẵn sàng dẫn đầu mọi cuộc chơi.",
                "Bạch Dương là cung đầu tiên, được cai trị bởi sao Hỏa. Họ có tính cách mạnh mẽ, quyết đoán và độc lập. Luôn tìm kiếm thử thách mới."));

        list.add(new Zodiac("Kim Ngưu", "20/04 - 20/05", R.drawable.kimnguu,
                "Kiên định, thực tế, đáng tin cậy và yêu thích sự ổn định. Họ coi trọng giá trị vật chất và thẩm mỹ.",
                "Kim Ngưu là cung Đất, được sao Kim cai trị. Họ có khả năng quản lý tài chính tốt, rất kiên nhẫn và bền bỉ. Yêu thích sự thoải mái và xa hoa."));

        list.add(new Zodiac("Song Tử", "21/05 - 20/06", R.drawable.songtu,
                "Thông minh, linh hoạt, thích giao tiếp và luôn tò mò về thế giới xung quanh.",
                "Song Tử là cung Khí, đại diện cho tính cách hai mặt. Họ có khả năng thích ứng nhanh, giỏi ăn nói và có khiếu hài hước. Luôn cần sự kích thích trí tuệ."));

        list.add(new Zodiac("Cự Giải", "21/06 - 22/07", R.drawable.cugiai,
                "Nhạy cảm, giàu tình cảm, chu đáo và luôn đặt gia đình lên hàng đầu.",
                "Cự Giải là cung Nước, được Mặt Trăng cai trị. Họ có trực giác mạnh mẽ, thích chăm sóc người khác và rất giàu trí tưởng tượng. Ngôi nhà và gia đình là nơi Cự Giải tìm thấy sự an toàn."));

        list.add(new Zodiac("Sư Tử", "23/07 - 22/08", R.drawable.sutu,
                "Hào phóng, ấm áp, tự tin và thích làm trung tâm sự chú ý.",
                "Sư Tử là cung Lửa, được Mặt Trời cai trị. Họ có tố chất lãnh đạo bẩm sinh, luôn tự tin và quyến rũ. Họ thích được ngưỡng mộ. Luôn tỏa sáng trong mọi hoàn cảnh."));

        list.add(new Zodiac("Xử Nữ", "23/08 - 22/09", R.drawable.xunu,
                "Cẩn thận, tỉ mỉ, thực tế và luôn muốn mọi thứ phải hoàn hảo.",
                "Xử Nữ là cung Đất, được sao Thủy cai trị. Họ có khả năng phân tích tuyệt vời, rất chăm chỉ và hữu ích. Tính cầu toàn cao."));

        list.add(new Zodiac("Thiên Bình", "23/09 - 22/10", R.drawable.thienbinh,
                "Yêu công bằng, hòa bình, duyên dáng và có gu thẩm mỹ tinh tế.",
                "Thiên Bình là cung Khí, được sao Kim cai trị. Họ là người hòa giải bẩm sinh, luôn tìm kiếm sự cân bằng và hài hòa. Giỏi ngoại giao."));

        list.add(new Zodiac("Bọ Cạp", "23/10 - 21/11", R.drawable.bocap,
                "Mạnh mẽ, bí ẩn, đam mê và có chiều sâu cảm xúc lớn.",
                "Bọ Cạp là cung Nước. Họ cực kỳ quyết tâm, có khả năng thấu hiểu sâu sắc và không dễ bị đánh bại. Luôn che giấu cảm xúc thật."));

        list.add(new Zodiac("Nhân Mã", "22/11 - 21/12", R.drawable.nhanma,
                "Lạc quan, yêu tự do, thích khám phá triết lý và sự thật.",
                "Nhân Mã là cung Lửa, được sao Mộc cai trị. Họ là những nhà thám hiểm, luôn khao khát kiến thức và trải nghiệm mới. Yêu thích sự tự do."));

        list.add(new Zodiac("Ma Kết", "22/12 - 19/01", R.drawable.maket,
                "Tham vọng, có trách nhiệm, kỷ luật và luôn hướng tới mục tiêu.",
                "Ma Kết là cung Đất, được sao Thổ cai trị. Họ có tinh thần trách nhiệm cao, rất chăm chỉ và thực tế. Thường đặt sự nghiệp lên hàng đầu."));

        list.add(new Zodiac("Bảo Bình", "20/01 - 18/02", R.drawable.baobinh,
                "Độc đáo, nhân đạo, sáng tạo và luôn hướng tới tương lai.",
                "Bảo Bình là cung Khí. Họ là những nhà cải cách, có tư tưởng tiến bộ và yêu thích sự khác biệt. Cần không gian cá nhân."));

        list.add(new Zodiac("Song Ngư", "19/02 - 20/03", R.drawable.songngu,
                "Giàu lòng trắc ẩn, mơ mộng, nghệ sĩ và có trực giác phi thường.",
                "Song Ngư là cung Nước. Họ là những người dễ đồng cảm, lãng mạn và có khả năng sáng tạo cao. Thường sống trong thế giới tưởng tượng."));

        return list;
    }
}