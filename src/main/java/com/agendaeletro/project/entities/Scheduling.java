package com.agendaeletro.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

@Entity // Anotação para definir que esta classe é uma entidade
public class Scheduling implements Serializable {
	private static final long serialVersionUID = 1L;

	/*
	 * A classe Scheduling representa um agendamento, ela implementa a interface
	 * Serializable para poder ser convertida em bytes e trafegar na rede, esta
	 * classe cria uma tabela scheduling no banco de dados, com as colunas
	 * correspondendo aos seus atributos
	 */

	@Id // Anotações para definir que este atributo é o id e tem auto increment
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// Anotações para o atributo initialDate para definir o padrão de data e hora
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Brazil/East")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, length = 254)
	private Date initialDate;
	// Anotações para o atributo finalDate para definir o padrão de data e hora
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm", timezone = "Brazil/East")
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, length = 254)
	private Date finalDate;

	@ManyToOne // Defininfo a relação de muitos para um com a tabela teacher
	@JoinColumn(name = "teacher_id") // criando uma coluna teacher_id
	private Teacher teacher;

	@ManyToOne // Defininfo a relação de muitos para um com a tabela classroom
	@JoinColumn(name = "classroom_id") // criando uma coluna classroom_id
	private Classroom classroom;

	@ManyToOne
	@JoinColumn(name = "class_id")
	private Class group;
	
	@ManyToMany // Defininfo a relação de muitos para muits com a tabela equipment
	@JoinTable(name = "equipments_schedulings", // criando tabela equipments_schedulings
			joinColumns = @JoinColumn(name = "scheduling_id"), // criando coluna scheduling_id
			inverseJoinColumns = @JoinColumn(name = "equipment_id")) // criando coluna equipment_id
	private List<Equipment> equipments = new ArrayList<>();

	// Construtor vazio
	public Scheduling() {
	}
	
	// Sobrecarga de construtor com parâmetros
	public Scheduling(Long id, Date initialDate, Date finalDate, Teacher teacher, Classroom classroom, Class group, List<Equipment> equipments) {
		this.id = id;
		this.initialDate = initialDate;
		this.finalDate = finalDate;
		this.teacher = teacher;
		this.classroom = classroom;
		this.group = group;
		addEquipments(equipments);
	}
	
	private void addEquipments(List<Equipment> equipments) {
		this.equipments.addAll(equipments);
	}

	// Método toString para retornar o agendamento em formato string
	@Override
	public String toString() {
		return String.format(
			"Scheduling: id=%s | initialDate=%s | finalDate=%s | teacher=%s | classroom=%s | class=%s | equipment=%s", id,
				initialDate, finalDate, teacher, classroom, group, equipments.toString());
	}

	// Getters e setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}
	
	public Date getFinalDate() {
		return finalDate;
	}
	
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}
	
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Classroom getClassroom() {
		return classroom;
	}
	
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	public List<Equipment> getEquipment() {
		return equipments;
	}
	
	public void addEquipment(Equipment equipment) {
		this.equipments.add(equipment);
	}
	
	public void clearEquipments() {
		this.equipments.clear();
	}

	public Class getGroup() {
		return group;
	}

	public void setGroup(Class group) {
		this.group = group;
	}

	//Método para comparar se o tempo é válido
	public boolean compareTime() {
		Date today = new Date(); // Pegar a data atual
		int diferenca = (int) TimeUnit.MILLISECONDS.toMinutes(finalDate.getTime() - initialDate.getTime());
		// Verificar se a data inicial é posterior a data final, ou data inicial é anterior a data atual e a diferença menor de 45 minutos
		return !initialDate.after(finalDate) && !initialDate.before(today) && diferenca >= 45;
	}

	// Métodos hashCode e equals para comprar objetos caso necessário
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scheduling other = (Scheduling) obj;
		return Objects.equals(id, other.id);
	}

}
