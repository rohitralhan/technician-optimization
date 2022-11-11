package org.acme.techassign.data;

import javax.enterprise.context.ApplicationScoped;
import org.acme.techassign.domain.TechJobAssign;

@ApplicationScoped
public class DataGenerator {

    // private static final AtomicLong NEXT_ID = new AtomicLong(0L);  

    public TechJobAssign generateTechJob() {
    	MySqlGetData ms = new MySqlGetData();
    	return new TechJobAssign(ms.getTechs(), ms.getJob());
    	//return new TechJobAssign(SKILLS, ms.getTechs(), ms.getJobs(0,0));
    }

}
