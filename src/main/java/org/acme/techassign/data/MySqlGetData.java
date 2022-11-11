package org.acme.techassign.data;

import javax.inject.Inject;

import org.acme.techassign.domain.Job;
import org.acme.techassign.domain.Skill;
import org.acme.techassign.domain.Tech;
import org.acme.techassign.domain.TechNJobs;
import org.acme.techassign.domain.Tool;

import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration.DataSourceImplementation;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.pool.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class MySqlGetData {

    @Inject
    AgroalDataSource ds;
    
    DataSource ds1;
    
    private static final AtomicLong NEXT_ID = new AtomicLong(0L);    
    
    private AgroalDataSourceConfigurationSupplier configuration = new AgroalDataSourceConfigurationSupplier()
            .dataSourceImplementation( DataSourceImplementation.AGROAL )
            .metricsEnabled( false )
            .connectionPoolConfiguration( cp -> cp
                    .minSize( 5 )
                    .maxSize( 20 )
                    .initialSize( 10 )
                    .connectionFactoryConfiguration( cf -> cf
                            .jdbcUrl( "jdbc:mysql://127.0.0.1:3306/techjob" )
                            .connectionProviderClassName( "com.mysql.cj.jdbc.Driver" )
                            .principal( new NamePrincipal( "root"))
                            .credential( new SimplePassword("Mysql@123" ))));
    
    private List<Job> jobs = new ArrayList<Job>();
    
    
	public MySqlGetData() {
		super();
		//TODO Auto-generated constructor stub
	}
    
    
	public List<Tech> getTechs() {
		
		try {
			Connection conn = getDBConn();
			Statement stmt = conn.createStatement();
			
			//String sql = "SELECT name, type, skills, tools, latitude, longitude, region, district, dispatchPolicy, selfAssigned, active, reqLunchBreak from tech order by name asc";
			String sql = "SELECT name, type, skills, tools, latitude, longitude, region, district, dispatchPolicy, selfAssigned, active, reqLunchBreak from tech1 order by name asc";
			
			ResultSet rs = stmt.executeQuery(sql);
			List<Tech> techs = new ArrayList<Tech>();

			while(rs.next()) {
				
				Set<Skill> skl = setSkills(rs.getString("skills"));
				Set<Tool> st = setTools(rs.getString("tools"));
				
				Tech t = new Tech(nextId(),rs.getString("name"), rs.getString("type"), skl, st, Double.parseDouble(rs.getString("latitude")),Double.parseDouble(rs.getString("longitude")),
								  rs.getString("region"), rs.getString("district"), rs.getString("dispatchPolicy"), 
								  rs.getString("selfAssigned"), rs.getString("active"), rs.getString("reqLunchBreak"));

				techs.add(t);
			}
            stmt.close();
            conn.close();
            return techs;
		} catch(Exception e) {
            //Handle errors for Class.forName 
            e.printStackTrace();
		}
		return null;
	}

	
	public List<Job> getJobs(int start, int count) { 
		
		try {
			Connection conn = getDBConn();
			Statement stmt = conn.createStatement();
			
			//String sql = "SELECT jobId, region, district, dispatchArea, lati, longi, requiredSkill, skillLevel, requiredTools, ispulljob, dispatch, priority, earlyStartDate, dueDate, status FROM techjob.jobs Limit " + start + "," + count + ";";
			String sql = "SELECT concat(substring(jobid,1,3), substring(jobid, -6, 6)) jobId, region, district, dispatchArea, lati, longi, requiredSkill, skillLevel, requiredTools, ispulljob, dispatch, priority, earlyStartDate, dueDate, status, SUBSTRING_INDEX(startDateTime, \" \", -1) starttime, substring(ADDTIME(SUBSTRING_INDEX(startDateTime, \" \", -1), SEC_TO_TIME(duration*60)),1,5) endtime, duration FROM techjob.jobs1;";

			ResultSet rs = stmt.executeQuery(sql);
			

			while(rs.next()) {
				
				Set<Skill> skl = setSkills(rs.getString("requiredSkill"), Integer.parseInt(rs.getString("skillLevel")));
				Set<Tool> st = setTools(rs.getString("requiredTools"));
				Job j = new Job(nextId(), rs.getString("jobId"), rs.getString("region"), rs.getString("district"), rs.getString("dispatchArea"), 
						rs.getString("lati"), rs.getString("longi"), skl, st, rs.getString("ispulljob"), rs.getString("dispatch"), 
						rs.getString("priority"), rs.getString("earlyStartDate"), rs.getString("dueDate"), rs.getString("status"), 
						rs.getString("starttime"), rs.getString("endtime") , Integer.parseInt(rs.getString("duration")));
				jobs.add(j);
			}
            stmt.close();
            conn.close();
            return jobs;
		} catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
		}		
		return null;
	}
	
	public Job getJob(int idx) { 
		if(jobs.isEmpty()) {
			getJobs(idx,1);
		}
		Job j1 = jobs.get(idx);
		Job j = new Job(nextId(), j1.getJobId(), j1.getRegion(), j1.getDistrict(), j1.getDispatchArea(), j1.getLati(), 
						j1.getLongi(), j1.getRequiredSkill(), j1.getRequiredTools(), j1.getIspulljob(), j1.getDispatch(), 
						j1.getPriority(), j1.getEarlyStartDate(), j1.getDueDate(), j1.getStatus(), j1.getStarttime(), j1.getEndtime(), j1.getDuration());
		
		return j;
	}

	public List<Job> getJob() {
		if(jobs.isEmpty()) {
			getJobs(0,1);
		}
		Job j1 = jobs.get(0);
		List<Job> j = new ArrayList<Job>();
		j.add(new Job(nextId(), j1.getJobId(), j1.getRegion(), j1.getDistrict(), j1.getDispatchArea(), j1.getLati(), 
						j1.getLongi(), j1.getRequiredSkill(), j1.getRequiredTools(), j1.getIspulljob(), j1.getDispatch(), 
						j1.getPriority(), j1.getEarlyStartDate(), j1.getDueDate(), j1.getStatus(), j1.getStarttime(), j1.getEndtime(), j1.getDuration()));
		
		return j;
	}	

	
	public List<TechNJobs> getTechsnJobs() {
		
		try {
			Connection conn = getDBConn();
			Statement stmt = conn.createStatement();
			
			String sql = "select name as id, region, district, latitude/1000000 as latitude, longitude/1000000 as longitude, concat(latitude/1000000, \",\", longitude/1000000) as latlong from tech1\n"
					+ "UNION\n"
					+ "select jobid as id, region, district, lati/1000000 as latitude, longi/1000000 as longitude, concat(lati/1000000, \",\", longi/1000000) as latlong from jobs1";
			
			ResultSet rs = stmt.executeQuery(sql);
			List<TechNJobs> techs = new ArrayList<TechNJobs>();

			while(rs.next()) {
				
				TechNJobs t = new TechNJobs(rs.getString("id"), rs.getString("region"), rs.getString("district"), Double.parseDouble(rs.getString("latitude")), Double.parseDouble(rs.getString("longitude")),
											rs.getString("latlong"));

				techs.add(t);
			}
            stmt.close();
            conn.close();
            return techs;
		} catch(Exception e) {
            //Handle errors for Class.forName 
            e.printStackTrace();
		}
		return null;
	}	
	
	
	private Set<Skill> setSkills(String skills) { 
		Set<Skill> ssk = new HashSet<Skill>();
		String[] sk = skills.split("\\|");
		for(String s : sk) {
			String[] skl = s.split("#");
			ssk.add(new Skill(skl[0].trim(), Integer.parseInt(skl[1].trim())));
		}
		return ssk;
	}
	
	private Set<Skill> setSkills(String skills, int skillLevel) { 
		Set<Skill> ssk = new HashSet<Skill>();
		String[] sk = skills.split("\\|");
		for(String s : sk) {
			ssk.add(new Skill(s.trim(), skillLevel));
		}
		return ssk;
	}	
	
	
	private Set<Tool> setTools(String tool) { 
		Set<Tool> stl = new HashSet<Tool>();
		String[] tl = tool.split("\\|");
		for(String t : tl) { 
			t=t.trim();
			stl.add(new Tool(t));
		}
		return stl;
	}
		
	
	private Connection getDBConn() {	
		try {
			if(ds == null) ds = AgroalDataSource.from( configuration );
			return ds.getConnection();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
    private static long nextId() {
        return NEXT_ID.getAndIncrement();
    }
	
	
	
}
