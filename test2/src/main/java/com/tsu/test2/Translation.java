package com.tsu.test2;

public class Translation {
    /*
    {
       "status":1,
        "msg":"古诗获取成功！",
        "data":{
                "id":151337,
                "subject":"次韵和黄茂笙病中之作",
                "dynasty":"近现代",
                "author":"连横",
                "content":"海上寒威迫，寰中战气酣。书来知病讯，诗好抵清谈。世事谁消长，生涯历苦甘。固园梅放未，酝酿一枝含。生死何须念，轮回悟已真。登天能證佛，入地即为人。兜率罡风乱，恒沙劫火频。祗今居畏垒，情话一酸辛。放眼高台上，风云孰主权？奇愁盘古剑，清泪坠春绵。丛桂能招隐，幽兰任弃捐。蓬莱非乐土，何事去求仙。自我归闾里，关门避世知。鱼潜青藻末，凤老碧梧枝。同病情逾重，论交道莫歧。滔滔东去水，坐待种桑时。"
                },
        "timestamp":1610029337038
     }761
    */
    int status;
    String msg;
    long timestamp;
    Data data;

    public class Data{
        long id;
        String subject;
        String dynasty;
        String author;
        String content;
    }

}

