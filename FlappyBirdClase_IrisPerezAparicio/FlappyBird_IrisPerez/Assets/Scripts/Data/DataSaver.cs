using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Runtime.Serialization.Formatters.Binary;
using System.IO;

public class DataSaver : MonoBehaviour
{
    public void Start()
    {
        Load();
    }

    public BirdController referenciaPajaro;
    // Start is called before the first frame update
    public void Save()
    {
        BinaryFormatter bf = new BinaryFormatter();
        FileStream file = File.Create(Application.persistentDataPath + "/MyGameData.dat");

        MyData sharedData = new MyData();
        sharedData.stats.score = referenciaPajaro.score;
        sharedData.stats.name = "luisja";

        bf.Serialize(file, sharedData);
        file.Close();
    }

    static public void Load()
    {
        if (File.Exists(Application.persistentDataPath + "/MyGameData.dat"))
        {
            BinaryFormatter bf = new BinaryFormatter();
            FileStream fs = File.Open(Application.persistentDataPath + "/MyGameData.dat", FileMode.Open);
            MyData sharedData = bf.Deserialize(fs) as MyData;
            fs.Close();

            if (sharedData != null)
            {
                Debug.Log("Vida actual: " + sharedData.stats.score);
                Debug.Log("Nombre: " + sharedData.stats.name);
            }
        }
    }
}
