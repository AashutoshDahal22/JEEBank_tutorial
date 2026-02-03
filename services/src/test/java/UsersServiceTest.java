import DTO.UsersDTO;
import DTO.UsersPatchDTO;
import exception.InvalidDataException;
import exception.InvalidEmailException;
import interfaces.users.UsersRepositoryInterface;
import models.UsersModel;
import org.mockito.ArgumentCaptor;
import service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

//    @Mock
//    private UsersMapper usersMapper;

    @Mock
    private UsersRepositoryInterface usersRepository;

    @InjectMocks
    private UsersService userService;

    private UsersDTO usersDTO;
    private UsersPatchDTO patchDTO;
    private UsersModel usersModel;

    @BeforeEach
    void setUp() {
        usersDTO = new UsersDTO();
        usersDTO.setId(1L);
        usersDTO.setName("Madara Uchiha");
        usersDTO.setEmail("random@gmail.com");
        usersDTO.setAddress("chandol");
        usersDTO.setPhoneNumber("9803133855");
        usersDTO.setBirthdate(LocalDate.of(2002, 1, 1));
    }

    @Test
    void successful_user_creation() {
        ArgumentCaptor<UsersModel> captor = ArgumentCaptor.forClass(UsersModel.class);

        userService.createUser(usersDTO);

        verify(usersRepository).insertUsers(captor.capture());
        UsersModel capturedModel = captor.getValue();

        assertEquals(usersDTO.getName(), capturedModel.getName());
        assertEquals(usersDTO.getEmail(), capturedModel.getEmail());
        assertEquals(usersDTO.getAddress(), capturedModel.getAddress());
        assertEquals(usersDTO.getPhoneNumber(), capturedModel.getPhoneNumber());
        assertEquals(usersDTO.getBirthdate(), capturedModel.getBirthdate());
    }

    @Test
    void null_user_details_creation() {
        usersDTO.setEmail(null);
        assertThrows(InvalidEmailException.class, () -> userService.createUser(usersDTO));
        verify(usersRepository, never()).insertUsers(any());
    }

    @Test
    void get_user_success() {
        userService.getUsersById(usersDTO.getId());
        verify(usersRepository).findUsersById(usersDTO.getId());
    }

    @Test
    void get_null_userid() {
        usersDTO.setId(null);
        assertThrows(InvalidDataException.class, () -> userService.getUsersById(usersDTO.getId()));
        verify(usersRepository, never()).findUsersById(any());
    }

    @Test
    void get_negative_userid() {
        usersDTO.setId(-1L);
        assertThrows(InvalidDataException.class, () -> userService.getUsersById(usersDTO.getId()));
        verify(usersRepository, never()).findUsersById(any());
    }

    @Test
    void delete_user_success() {
        userService.deleteUsersById(usersDTO.getId());
        verify(usersRepository).deleteUsersById(usersDTO.getId());
    }

    @Test
    void delete_null_userid() {
        usersDTO.setId(null);
        assertThrows(InvalidDataException.class, () -> userService.deleteUsersById(usersDTO.getId()));
        verify(usersRepository, never()).deleteUsersById(any());
    }

    @Test
    void delete_negative_userid() {
        usersDTO.setId(-1L);
        assertThrows(InvalidDataException.class, () -> userService.deleteUsersById(usersDTO.getId()));
        verify(usersRepository, never()).deleteUsersById(any());
    }

    @Test
    void getAllUsers_success() {
        int page = 10;
        int size = 2;
        int offset = (page - 1) * size;
        userService.getAllUsers(page, size);
        verify(usersRepository).getAllUsers(offset, size);
    }

    @Test
    void getAllUsers_whenNoAccounts_returnsEmptyList() {
        int page = 10;
        int size = 2;
        int offset = (page - 1) * size;
        when(usersRepository.getAllUsers(offset, size)).thenReturn(Collections.emptyList());
        List<UsersModel> result = userService.getAllUsers(page, size);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
