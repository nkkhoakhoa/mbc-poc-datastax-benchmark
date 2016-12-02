package com.nkk.gremlin.api;

import com.nkk.gremlin.api.data.draft.DraftUserData;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author KhoaNguyenKieu
 */
public class Application {

    private static final Executor exe = Executors.newFixedThreadPool(8);

    public static void main(String... args) {

    }

    public void initUserData() {
        final DraftUserData userData = new DraftUserData();
        for(int i = 1; i < 5000; i++) {
            final int j = i;
            exe.execute(new Runnable() {
                public void run() {
                    userData.createDraftData(5000000 + j * 1000, 5000000 + (j + 1) * 1000);
                }
            });
        }
    }
}
