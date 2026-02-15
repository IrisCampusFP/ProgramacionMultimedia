using UnityEngine;
using UnityEngine.SceneManagement;
using TMPro;

public class MainMenu : MonoBehaviour
{
    public TMP_Text bestScoreText;

    void Start()
    {
        ActualizarBestScore();
    }

    void ActualizarBestScore()
    {
        int bestScore = PlayerPrefs.GetInt("bestScore", 0);
        bestScoreText.text = "Best score: " + bestScore.ToString();
    }

    public void jugar()
    {
        SceneManager.LoadScene(1); // Cargar escena del juego
    }

    public void salir()
    {
        Application.Quit();
    }
}
