package pro.cvitae.gmailrelayer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pro.cvitae.gmailrelayer.persistence.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {

    /**
     * Returns an {@link Address} by the value of the email address
     *
     * @param addressText
     * @return
     */
    Address findByValue(String addressText);
}
