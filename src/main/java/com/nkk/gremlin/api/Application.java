package com.nkk.gremlin.api;

import com.nkk.gremlin.api.data.draft.DraftUserData;

/**
 * @author KhoaNguyenKieu
 */
public class Application {

    public static void main(final String[] args) {

        final DraftUserData userData = new DraftUserData();
        userData.createDraftData(3400, 4000);
        System.out.println("Done!");

    }

}
