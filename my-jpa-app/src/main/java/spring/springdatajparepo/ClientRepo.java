package spring.springdatajparepo;

import org.springframework.data.jpa.repository.JpaRepository;

/*
* JpaRepository has abstract methods: save, findAll, findById, count, deleteById etc.
* The IoC container will create and entity manager based implementation automatically
* In a non-Spring boot app we would have to add @EnableJpaRepositories alongside @Configuration
* @EnableJpaRepositories instructs the container to scan for JpaRepository interfaces and create the implementation
* How does the container know how to code an implementation for e.g. findall?
* The interface refs the entity class - Client and Client has entity mapping annotations.
* From this the container can build the necessary JPQL query e.g. select * from Client;
*
* NB: DONT ANNOTATE THE INTERFACE AS A REPO/COMPONENT
 */

public interface ClientRepo extends JpaRepository<Client, Long> {


}

