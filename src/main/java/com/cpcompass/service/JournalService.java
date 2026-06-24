package com.cpcompass.service;

import com.cpcompass.dto.journal.CreateJournalRequest;
import com.cpcompass.dto.journal.JournalResponse;
import com.cpcompass.entity.JournalEntry;
import com.cpcompass.entity.User;
import com.cpcompass.repository.JournalRepository;
import com.cpcompass.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;
    private final UserRepository userRepository;

    public JournalResponse createEntry(
            CreateJournalRequest request
    ) {

        User user = getCurrentUser();

        JournalEntry entry =
                JournalEntry.builder()
                        .problemName(
                                request.getProblemName()
                        )
                        .problemUrl(
                                request.getProblemUrl()
                        )
                        .topic(
                                request.getTopic()
                        )
                        .whatILearned(
                                request.getWhatILearned()
                        )
                        .mistakes(
                                request.getMistakes()
                        )
                        .observation(
                                request.getObservation()
                        )
                        .user(user)
                        .build();

        JournalEntry savedEntry =
                journalRepository.save(entry);

        return mapToResponse(savedEntry);
    }

    public JournalResponse updateEntry(
            Long entryId,
            CreateJournalRequest request
    ) {

        User user = getCurrentUser();

        JournalEntry entry =
                journalRepository.findById(entryId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Journal entry not found"
                                )
                        );

        if (!entry.getUser().getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        entry.setProblemName(
                request.getProblemName()
        );

        entry.setProblemUrl(
                request.getProblemUrl()
        );

        entry.setTopic(
                request.getTopic()
        );

        entry.setWhatILearned(
                request.getWhatILearned()
        );

        entry.setMistakes(
                request.getMistakes()
        );

        entry.setObservation(
                request.getObservation()
        );

        JournalEntry updatedEntry =
                journalRepository.save(entry);

        return mapToResponse(updatedEntry);
    }

    public List<JournalResponse> getAllEntries() {

        User user = getCurrentUser();

        return journalRepository
                .findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public JournalResponse getEntryById(
            Long entryId
    ) {

        User user = getCurrentUser();

        JournalEntry entry =
                journalRepository.findById(entryId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Journal entry not found"
                                )
                        );

        if (!entry.getUser().getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        return mapToResponse(entry);
    }

    public void deleteEntry(
            Long entryId
    ) {

        User user = getCurrentUser();

        JournalEntry entry =
                journalRepository.findById(entryId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Journal entry not found"
                                )
                        );

        if (!entry.getUser().getId()
                .equals(user.getId())) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        journalRepository.delete(entry);
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String email =
                authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );
    }

    private JournalResponse mapToResponse(
            JournalEntry entry
    ) {

        return JournalResponse.builder()
                .id(
                        entry.getId()
                )
                .problemName(
                        entry.getProblemName()
                )
                .problemUrl(
                        entry.getProblemUrl()
                )
                .topic(
                        entry.getTopic()
                )
                .whatILearned(
                        entry.getWhatILearned()
                )
                .mistakes(
                        entry.getMistakes()
                )
                .observation(
                        entry.getObservation()
                )
                .createdAt(
                        entry.getCreatedAt()
                )
                .build();
    }
}