/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ldxx.xxalib.adapter;

/**
 * Presentation model created to contain all the information needed to draw the footer of a list of
 * DragonBall characters.
 */
public class Footer {

  private final String loadMoreMessage;

  public Footer(String loadMoreMessage) {
    this.loadMoreMessage = loadMoreMessage;
  }

  public String getLoadMoreMessage() {
    return loadMoreMessage;
  }
}
