package Chat

import Chat.Token.*
import Utils.SpellCheckerService

class TokenizerService(spellCheckerSvc: SpellCheckerService):
  /**
    * Separate the user's input into tokens
    * @param input The user's input
    * @return A Tokenizer which allows iteration over the tokens of the input
    */
  // TODO - Part 2 Step 1
  def tokenize(input: String): Tokenized =
    val normalizedTokens = input.replaceAll("[,.!?*]", "")
      .replaceAll("\'", " ")
      .split("\\s+") // one or more space
      .map(rawToken => spellCheckerSvc.dictionary.getOrElse(rawToken, spellCheckerSvc.getClosestWordInDictionary(rawToken)))

    TokenizedImpl(normalizedTokens.map(s => (s, getToken(s))))
  end tokenize

  /**
    * return a Token based on a string
    * @param word a string word
    * @return a Token
    */
  def getToken(word: String): Token = {
    word match {
      case "bonjour" => Token.BONJOUR
      case "je" => Token.JE
      case "svp" => Token.SVP
      case "assoiffe" => Token.ASSOIFFE
      case "affame" => Token.AFFAME
      // Actions
      case "etre" => Token.ETRE
      case "vouloir" => Token.VOULOIR
      // Logic Operators
      case "et" => Token.ET
      case "ou" => Token.OU
      // Products
      case "biere" => Token.PRODUCT
      case "croissant" => Token.PRODUCT
      // Util
      case _ if word.head == '_' => Token.PSEUDO
      case _ if word.toDoubleOption.isDefined => Token.NUM
      case _ => Token.UNKNOWN
    }
  }
end TokenizerService
