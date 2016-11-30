package com.nkk.gremlin.api.data.draft;

import com.datastax.driver.dse.DseCluster;
import com.datastax.driver.dse.DseSession;
import com.nkk.gremlin.api.domain.entities.User;
import com.nkk.gremlin.api.dse.DSEFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author KhoaNguyenKieu
 */
public class DraftUserData implements DraftData {

    private static final Logger LOGGER = LoggerFactory.getLogger(DraftUserData.class);

    private static String[] GENDERS = new String[] {"Male", "Female"};

    public void createDraftData(int start, int end) {
        final DseCluster cluster = new DSEFactory().getCluster();
        final DseSession session = cluster.newSession();
        final User user = new User();
        for (int i = start; i < end; i++) {
            setProperties(user, i);
            session.executeGraph(user.getInsertCommand());
            if (i % 100 == 0 ) {
                LOGGER.info("Inserted user id: " + i);
            }
        }
        session.close();
        cluster.close();
    }

    public void createDraftData(int count) {
        createDraftData(0, count);
    }

    private void setProperties(final User user, int i) {
        user.setUsername("user" + i);
        user.setEmail(user.getUsername() + "@mbc.net");
        user.setGender(GENDERS[getRandom()]);
    }

    private int getRandom() {
        return Math.random() < 0.5 ? 0 : 1;
    }

}
