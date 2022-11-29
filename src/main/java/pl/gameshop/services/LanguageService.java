package pl.gameshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.gameshop.models.Language;

public interface LanguageService {

    Page<Language> getAllLanguages(Pageable pageable);

    Language getLanguage(Long id);

    void deleteLanguage(Long id);

    void saveLanguage(Language language);
}
