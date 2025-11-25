package br.com.infnet.integration;

import br.com.infnet.dto.PersonDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DataSyncService {

    private final List<PersonDTO> syncBuffer = new CopyOnWriteArrayList<>();
    private final SystemIntegration integration;

    public DataSyncService(SystemIntegration integration) {
        this.integration = integration;
    }

    public void addToSync(PersonDTO person) {
        syncBuffer.add(person);
    }

    public List<PersonDTO> getSyncBuffer() {
        return new ArrayList<>(syncBuffer);
    }

    public void flushSync() {
        for (PersonDTO dto : syncBuffer) {
            integration.savePerson(dto);
        }
        syncBuffer.clear();
    }

    public int getSyncCount() {
        return syncBuffer.size();
    }
}