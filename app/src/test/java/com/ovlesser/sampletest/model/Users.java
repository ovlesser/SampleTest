package com.ovlesser.sampletest.model;

import kotlin.jvm.JvmStatic;

public class Users {
    public User[] users = new User[]{
            new User(1, "Leanne Graham",
                    "Bret",
                    "Sincere@april.biz",
                    new Address("Kulas Light",
                            "Apt. 556",
                            "Gwenborough",
                            "92998-3874",
                            new Geo("-37.3159",
                                    "81.1496")),
                    "1-770-736-8031 x56442",
                    "hildegard.org",
                    new Company("Romaguera-Crona",
                            "Multi-layered client-server neural-net",
                            "harness real-time e-markets")
            ),
            new User(2, "Ervin Howell",
                    "Antonette",
                    "Shanna@melissa.tv",
                    new Address("Victor Plains",
                            "Suite 879",
                            "Wisokyburgh",
                            "90566-7771",
                            new Geo("-43.9509",
                                    "-34.4618")),
                    "010-692-6593 x09125",
                    "anastasia.net",
                    new Company("Deckow-Crist",
                            "Proactive didactic contingency",
                            "synergize scalable supply-chains")
            )
    };
}
