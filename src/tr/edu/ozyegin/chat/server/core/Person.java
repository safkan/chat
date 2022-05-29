package tr.edu.ozyegin.chat.server.core;

import java.time.LocalDateTime;

public class Person {
	private String name;
	private LocalDateTime lastActivity;
	
	public Person(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj instanceof Person) {
			Person other = (Person)obj;
			return this.name.equals(other.name);
		} else {
			return false;
		}
	
	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	public LocalDateTime getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(LocalDateTime lastActivity) {
		this.lastActivity = lastActivity;
	}
	
	
	
}
