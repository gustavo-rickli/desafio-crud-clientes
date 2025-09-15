package com.gustavorickli.crud_clientes.service;

import com.gustavorickli.crud_clientes.dto.ClienteDTO;
import com.gustavorickli.crud_clientes.entity.Cliente;
import com.gustavorickli.crud_clientes.repository.ClienteRepository;
import com.gustavorickli.crud_clientes.service.exceptions.DatabaseException;
import com.gustavorickli.crud_clientes.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("cliente inexistente"));

        return new ClienteDTO(cliente);
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> clientes = repository.findAll(pageable);

        return clientes.map(cliente -> new ClienteDTO(cliente));
    }

    @Transactional
    public ClienteDTO insert(@Valid @RequestBody ClienteDTO clienteDTO) {

        Cliente cliente = new Cliente(clienteDTO);

        cliente = repository.save(cliente);

        return new ClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {

        try {

            Cliente cliente = repository.getReferenceById(id);
            copyDtoToEntity(clienteDTO, cliente);

            cliente = repository.save(cliente);

            return new ClienteDTO(cliente);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("cliente inexistente");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("recurso nao encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }


    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setChildren(dto.getChildren());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
    }

}
