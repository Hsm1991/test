package tn.esprit.spring.khaddem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.khaddem.entities.Departement;
import tn.esprit.spring.khaddem.entities.Etudiant;
import tn.esprit.spring.khaddem.repositories.ContratRepository;
import tn.esprit.spring.khaddem.repositories.DepartementRepository;
import tn.esprit.spring.khaddem.repositories.EquipeRepository;
import tn.esprit.spring.khaddem.repositories.EtudiantRepository;
import tn.esprit.spring.khaddem.services.EtudiantServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
public class EtudiantServiceImplTest {
    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    @Mock
    private EtudiantRepository etudiantRepository;

    @Mock
    private ContratRepository contratRepository;

    @Mock
    private EquipeRepository equipeRepository;

    @Mock
    private DepartementRepository departementRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllEtudiants() {
        // Create a list of Etudiant objects for testing
        List<Etudiant> etudiants = new ArrayList<>();
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        etudiants.add(etudiant1);
        etudiants.add(etudiant2);

        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();

        // Vérification du résultat
        if (etudiants.size() == result.size()) {
            log.info("Le test a réussi : La taille de la liste correspond.");
        } else {
            log.error("Le test a échoué : La taille de la liste ne correspond pas.");
        }

        // Utilisez des assertions pour vérifier les conditions attendues
        assertEquals(etudiants.size(), result.size()+1);
    }
    @Test
    public void testAddEtudiant() {
        Etudiant etudiant = new Etudiant(/* Initialize etudiant object */);

        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);
        assertNotNull(result);
    }

    // Add similar test methods for other service methods...

    @Test
    public void testAssignEtudiantToDepartement() {
        Integer etudiantId = 1;
        Integer departementId = 2;
        Etudiant etudiant = new Etudiant(/* Initialize etudiant object */);
        Departement departement = new Departement(/* Initialize departement object */);

        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
        when(departementRepository.findById(departementId)).thenReturn(Optional.of(departement));

        etudiantService.assignEtudiantToDepartement(etudiantId, departementId);

        assertEquals(departement, etudiant.getDepartement());
        verify(etudiantRepository, times(1)).save(etudiant);
    }
}
