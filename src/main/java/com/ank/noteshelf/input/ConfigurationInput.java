package com.ank.noteshelf.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfigurationInput {

  private String configurationName;
  private String configurationValue;
  private boolean lazyLoadDisabled;
  
}
