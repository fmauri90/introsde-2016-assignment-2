package ehealth.model;

import ehealth.dao.LifeCoachDao;
import ehealth.model.Person;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The persistent class for the "HealthMeasureHistory" database table.
 * 
 */
@Entity
@Table(name="\"HealthMeasureHistory\"")
@NamedQueries({
	@NamedQuery(name="HealthMeasureHistory.findAll", query="SELECT h FROM HealthMeasureHistory h"),
	@NamedQuery(name="HealthMeasureHistory.findByMeasure", query="SELECT h FROM HealthMeasureHistory h WHERE h.person = ?1 AND h.measureDefinition = ?2"),
	@NamedQuery(name="HealthMeasureHistory.findByMeasureDate", query="SELECT h FROM HealthMeasureHistory h WHERE h.person = ?1 AND h.measureDefinition = ?2 "
			+ "AND h.timestamp BETWEEN ?4 AND ?3")
})
@XmlType(propOrder={"idMeasureHistory", "value" , "timestamp"})
@JsonPropertyOrder({ "mid", "value", "created"})
@XmlRootElement(name="measure")

public class HealthMeasureHistory implements Serializable{
	   private static final long serialVersionUID = 1L;
	   
	   @Id
	   @GeneratedValue(generator="sqlite_mhistory")
	   @TableGenerator(name="sqlite_mhistory", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq",
	    pkColumnValue="HealthMeasureHistory")
	   @Column(name="\"idMeasureHistory\"")
	   
	   private int idMeasureHistory;
	   
	   @Temporal(TemporalType.DATE)
	   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	   @Column(name="\"timestamp\"")
	   private Date timestamp;
	   
	   @Column(name="\"value\"")
	   private String value;
	   
	   @ManyToOne
	   @JoinColumn(name = "\"idMeasureDef\"", referencedColumnName = "\"idMeasureDef\"")
	   private MeasureDefinition measureDefinition;
	   
	   // notice that we haven't included a reference to the history in Person
	   // this means that we don't have to make this attribute XmlTransient
	   
	   @ManyToOne
	   @JoinColumn(name = "\"idPerson\"", referencedColumnName = "\"idPerson\"")
	   private Person person;
	   
	   public HealthMeasureHistory() {
	   }
	   
	   @XmlElement(name = "mid")
	   public int getIdMeasureHistory(){
		   return this.idMeasureHistory;
	   }
	   
	   public void setIdMeasureHistory(int idMeasureHistory){
		   this.idMeasureHistory = idMeasureHistory;
	   }
	   
	   @XmlElement(name = "created")
	   public Date getTimestamp(){
		   return this.timestamp;
	   }
	   
	   public void setTimestamp(Date timestamp){
		   this.timestamp = timestamp;
	   }
	   
	   public String getValue(){
		   return this.value;
	   }
	   
	   public void setValue(String value){
		   this.value = value;
	   }
	   
	   @XmlTransient
	   public MeasureDefinition getMeasureDefinition(){
		   return measureDefinition;
	   }
	   
	   public void setMeasureDefinition(MeasureDefinition param){
		   this.measureDefinition = param;
	   }
	   
	   @XmlTransient
	   public Person getPerson(){
		   return person;
	   }
	   
	   public void setPerson(Person param){
		   this.person = param;
	   }
	   
	   //database operations
	   public static HealthMeasureHistory getHealthMeasureHistoryById(int id){
		   EntityManager em = LifeCoachDao.instance.createEntityManager();
		   HealthMeasureHistory p = em.find(HealthMeasureHistory.class, id);
		   LifeCoachDao.instance.closeConnections(em);
		   return p;
	   }
	   
	   public static List<HealthMeasureHistory> getAll(){
		   EntityManager em = LifeCoachDao.instance.createEntityManager();
		    List<HealthMeasureHistory> list = em.createNamedQuery("HealthMeasureHistory.findAll", HealthMeasureHistory.class).getResultList();
		    LifeCoachDao.instance.closeConnections(em);
		    return list;
	   }
	   
	   /**
		 * Returns the history of a measureType for a person
		 * @param p
		 * @param md
		 * @return list of HealthMeasureHistory
		 */
	   
	   public static List<HealthMeasureHistory> getByPersonMeasure(Person p, MeasureDefinition md){
		   EntityManager em = LifeCoachDao.instance.createEntityManager();
		   TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByMeasure", HealthMeasureHistory.class);
		   query.setParameter(1, p);
		   query.setParameter(2, md);
		   List<HealthMeasureHistory> list = query.getResultList();
		   LifeCoachDao.instance.closeConnections(em);
		   return list;
	   }
	   
	   public static HealthMeasureHistory saveHealthMeasureHistory(HealthMeasureHistory p){
		   EntityManager em = LifeCoachDao.instance.createEntityManager();
		   EntityTransaction tx = em.getTransaction();
		   tx.begin();
		   p = em.merge(p);
		   tx.commit();
		   LifeCoachDao.instance.closeConnections(em);
		   return p;
	   }
	   
	   public static HealthMeasureHistory updateHealthMeasureHistory(HealthMeasureHistory p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			p=em.merge(p);
			tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
		    return p;
	   }
	   
	   public static void removeHealthMeasureHistory(HealthMeasureHistory p) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			EntityTransaction tx = em.getTransaction();
			tx.begin();
		    p=em.merge(p);
		    em.remove(p);
		    tx.commit();
		    LifeCoachDao.instance.closeConnections(em);
	   }
	   
	   /**
		 * Returns the history in a specified range of date for a specific person
		 * @param person
		 * @param md MeasureDefinition
		 * @param before representing the end date of the range
		 * @param after representing the start date of the range
		 * @return list of HelthMeasureHistory elements
		 */
	   
	   public static List<HealthMeasureHistory> getByPersonMeasureDate(Person person, MeasureDefinition md, Calendar before,Calendar after) {
			EntityManager em = LifeCoachDao.instance.createEntityManager();
			TypedQuery<HealthMeasureHistory> query = em.createNamedQuery("HealthMeasureHistory.findByMeasureDate", HealthMeasureHistory.class);
			query.setParameter(1, person);
			query.setParameter(2, md);
			query.setParameter(3, before.getTime());
			query.setParameter(4, after.getTime());
			
			List<HealthMeasureHistory> list = query.getResultList();
		    LifeCoachDao.instance.closeConnections(em);
			return list;
		}
}