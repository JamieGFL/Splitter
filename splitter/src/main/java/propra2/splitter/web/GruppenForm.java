package propra2.splitter.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GruppenForm {

  @NotNull
  @NotBlank
  @NotEmpty
  @Size(min = 5 , max = 30, message = "Mindestens 5 und maximal 30 Zeichen")
  private String gruppenName;

  public GruppenForm(String gruppenName) {
    this.gruppenName = gruppenName;
  }

  public String getGruppenName() {
    return gruppenName;
  }

  public void setGruppenName(String gruppenName) {
    this.gruppenName = gruppenName;
  }
}
