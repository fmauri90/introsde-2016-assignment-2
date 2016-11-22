package ehealth.model;

import ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@Entity // indicates that this class is an entity to persist in DB
@Table(name="\"Person\"") // to whole table must be persisted
@NamedQueries({
@NamedQuery(name="Person.findAll", query="SELECT p FROM  Person p"),
@NamedQuery(name="Person.findByMeasureNameMinMax", 
				query="SELECT p FROM Person p INNER JOIN p.lifeStatus l WHERE l.measureDefinition = ?1 AND "
						+ "CAST(l.value NUMERIC(10,2)) BETWEEN ?2 AND ?3")
})

@XmlRootElement
@XmlType(propOrder={"idPerson", "name", "lastname" , "birthdate", "lifeStatus"})
@JsonPropertyOrder({ "idPerson", "firstname", "lastname" , "birthdate", "lifeStatus"})

public class Person implements Serializable{
	public static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")
    @Column(name="\"idPerson\"")
    private int idPerson;
    @Column(name="\"lastname\"")
    private String lastname;
    @Column(name="\"name\"")
    private String name;
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @Column(name="\"birthdate\"")
    private Date birthdate;
    
    
    // mappedBy must be equal to the name of the attribute in LifeStatus that maps this relation
    @OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    private List<LifeStatus> lifeStatus;
    
    @XmlElementWrapper(name = "healthprofile")
    @XmlElement(name="measureType")
    @JsonProperty("healthprofile")
    public List<LifeStatus> getLifeStatus() {
        return lifeStatus;
    }
    // add below all the getters and setters of all the private attributes
    
    public void setLifeStatus(List<LifeStatus> lifeStatus){
    	this.lifeStatus = lifeStatus ;
    }
    
    // getters
    public int getIdPerson(){
        return idPerson;
    }

    public String getLastname(){
        return lastname;
    }
    @XmlElement(name="firstname")
    public String getName(){
        return name;
    }
    
    public Date getBirthdate(){
        return birthdate;
    }
    
    // setters
    public void setIdPerson(int idPerson){
        this.idPerson = idPerson;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public void setBirthdate(Date birthdate){
        this.birthdate = birthdate;
    }
        
    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
            .getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
    }

    public static Person savePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    } 

    public static Person updatePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager(); 
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static void removePerson(Person p) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        p=em.merge(p);
        em.remove(p);
        tx.commit();
        LifeCoachDao.instance.closeConnections(em);
    }
    /**
     * Returns the list of people whose measure is in the range min-max
     * @param md MeasureDefinition
     * @param min minimum value	
     * @param max maximum value
     * @return list of "Person" object
     */
	public static List<Person> getByMeasureNameMinMax(MeasureDefinition md, Double min, Double max) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
        em.getEntityManagerFactory().getCache().evictAll();
        List<Person> list = em.createNamedQuery("Person.findByMeasureNameMinMax", Person.class)
        		.setParameter(1, md)
        		.setParameter(2, min)
        		.setParameter(3, max)
        		.getResultList();
        LifeCoachDao.instance.closeConnections(em);
        return list;
	}
    
}