package pro.cvitae.gmailrelayer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.cvitae.gmailrelayer.persistence.entity.Header;

public interface HeaderDao extends JpaRepository<Header, Long> {

}
